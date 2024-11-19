import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVulnerabilidades, NewVulnerabilidades } from '../vulnerabilidades.model';

export type PartialUpdateVulnerabilidades = Partial<IVulnerabilidades> & Pick<IVulnerabilidades, 'id'>;

export type EntityResponseType = HttpResponse<IVulnerabilidades>;
export type EntityArrayResponseType = HttpResponse<IVulnerabilidades[]>;

@Injectable({ providedIn: 'root' })
export class VulnerabilidadesService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vulnerabilidades');

  create(vulnerabilidades: NewVulnerabilidades): Observable<EntityResponseType> {
    return this.http.post<IVulnerabilidades>(this.resourceUrl, vulnerabilidades, { observe: 'response' });
  }

  update(vulnerabilidades: IVulnerabilidades): Observable<EntityResponseType> {
    return this.http.put<IVulnerabilidades>(
      `${this.resourceUrl}/${this.getVulnerabilidadesIdentifier(vulnerabilidades)}`,
      vulnerabilidades,
      { observe: 'response' },
    );
  }

  partialUpdate(vulnerabilidades: PartialUpdateVulnerabilidades): Observable<EntityResponseType> {
    return this.http.patch<IVulnerabilidades>(
      `${this.resourceUrl}/${this.getVulnerabilidadesIdentifier(vulnerabilidades)}`,
      vulnerabilidades,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVulnerabilidades>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVulnerabilidades[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVulnerabilidadesIdentifier(vulnerabilidades: Pick<IVulnerabilidades, 'id'>): number {
    return vulnerabilidades.id;
  }

  compareVulnerabilidades(o1: Pick<IVulnerabilidades, 'id'> | null, o2: Pick<IVulnerabilidades, 'id'> | null): boolean {
    return o1 && o2 ? this.getVulnerabilidadesIdentifier(o1) === this.getVulnerabilidadesIdentifier(o2) : o1 === o2;
  }

  addVulnerabilidadesToCollectionIfMissing<Type extends Pick<IVulnerabilidades, 'id'>>(
    vulnerabilidadesCollection: Type[],
    ...vulnerabilidadesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vulnerabilidades: Type[] = vulnerabilidadesToCheck.filter(isPresent);
    if (vulnerabilidades.length > 0) {
      const vulnerabilidadesCollectionIdentifiers = vulnerabilidadesCollection.map(vulnerabilidadesItem =>
        this.getVulnerabilidadesIdentifier(vulnerabilidadesItem),
      );
      const vulnerabilidadesToAdd = vulnerabilidades.filter(vulnerabilidadesItem => {
        const vulnerabilidadesIdentifier = this.getVulnerabilidadesIdentifier(vulnerabilidadesItem);
        if (vulnerabilidadesCollectionIdentifiers.includes(vulnerabilidadesIdentifier)) {
          return false;
        }
        vulnerabilidadesCollectionIdentifiers.push(vulnerabilidadesIdentifier);
        return true;
      });
      return [...vulnerabilidadesToAdd, ...vulnerabilidadesCollection];
    }
    return vulnerabilidadesCollection;
  }
}
