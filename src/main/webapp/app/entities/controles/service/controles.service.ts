import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControles, NewControles } from '../controles.model';

export type PartialUpdateControles = Partial<IControles> & Pick<IControles, 'id'>;

export type EntityResponseType = HttpResponse<IControles>;
export type EntityArrayResponseType = HttpResponse<IControles[]>;

@Injectable({ providedIn: 'root' })
export class ControlesService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controles');

  create(controles: NewControles): Observable<EntityResponseType> {
    return this.http.post<IControles>(this.resourceUrl, controles, { observe: 'response' });
  }

  update(controles: IControles): Observable<EntityResponseType> {
    return this.http.put<IControles>(`${this.resourceUrl}/${this.getControlesIdentifier(controles)}`, controles, { observe: 'response' });
  }

  partialUpdate(controles: PartialUpdateControles): Observable<EntityResponseType> {
    return this.http.patch<IControles>(`${this.resourceUrl}/${this.getControlesIdentifier(controles)}`, controles, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IControles>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IControles[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControlesIdentifier(controles: Pick<IControles, 'id'>): number {
    return controles.id;
  }

  compareControles(o1: Pick<IControles, 'id'> | null, o2: Pick<IControles, 'id'> | null): boolean {
    return o1 && o2 ? this.getControlesIdentifier(o1) === this.getControlesIdentifier(o2) : o1 === o2;
  }

  addControlesToCollectionIfMissing<Type extends Pick<IControles, 'id'>>(
    controlesCollection: Type[],
    ...controlesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controles: Type[] = controlesToCheck.filter(isPresent);
    if (controles.length > 0) {
      const controlesCollectionIdentifiers = controlesCollection.map(controlesItem => this.getControlesIdentifier(controlesItem));
      const controlesToAdd = controles.filter(controlesItem => {
        const controlesIdentifier = this.getControlesIdentifier(controlesItem);
        if (controlesCollectionIdentifiers.includes(controlesIdentifier)) {
          return false;
        }
        controlesCollectionIdentifiers.push(controlesIdentifier);
        return true;
      });
      return [...controlesToAdd, ...controlesCollection];
    }
    return controlesCollection;
  }
}
