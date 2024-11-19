import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoActivos, NewGrupoActivos } from '../grupo-activos.model';
import { GrupoActivosFormGroup } from '../update/grupo-activos-form.service';

export type PartialUpdateGrupoActivos = Partial<IGrupoActivos> & Pick<IGrupoActivos, 'id'>;

export type EntityResponseType = HttpResponse<IGrupoActivos>;
export type EntityArrayResponseType = HttpResponse<IGrupoActivos[]>;

@Injectable({ providedIn: 'root' })
export class GrupoActivosService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-activos');

  create(grupoActivos: NewGrupoActivos): Observable<EntityResponseType> {
    return this.http.post<IGrupoActivos>(this.resourceUrl, grupoActivos, { observe: 'response' });
  }

  update(grupoActivos: IGrupoActivos): Observable<EntityResponseType> {
    return this.http.put<IGrupoActivos>(`${this.resourceUrl}/${this.getGrupoActivosIdentifier(grupoActivos)}`, grupoActivos, {
      observe: 'response',
    });
  }

  partialUpdate(grupoActivos: PartialUpdateGrupoActivos): Observable<EntityResponseType> {
    return this.http.patch<IGrupoActivos>(`${this.resourceUrl}/${this.getGrupoActivosIdentifier(grupoActivos)}`, grupoActivos, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoActivos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoActivos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoActivosIdentifier(grupoActivos: Pick<IGrupoActivos, 'id'>): number {
    return grupoActivos.id;
  }

  compareGrupoActivos(o1: Pick<IGrupoActivos, 'id'> | null, o2: Pick<IGrupoActivos, 'id'> | null): boolean {
    return o1 && o2 ? this.getGrupoActivosIdentifier(o1) === this.getGrupoActivosIdentifier(o2) : o1 === o2;
  }

  addGrupoActivosToCollectionIfMissing<Type extends Pick<IGrupoActivos, 'id'>>(
    grupoActivosCollection: Type[],
    ...grupoActivosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoActivos: Type[] = grupoActivosToCheck.filter(isPresent);
    if (grupoActivos.length > 0) {
      const grupoActivosCollectionIdentifiers = grupoActivosCollection.map(grupoActivosItem =>
        this.getGrupoActivosIdentifier(grupoActivosItem),
      );
      const grupoActivosToAdd = grupoActivos.filter(grupoActivosItem => {
        const grupoActivosIdentifier = this.getGrupoActivosIdentifier(grupoActivosItem);
        if (grupoActivosCollectionIdentifiers.includes(grupoActivosIdentifier)) {
          return false;
        }
        grupoActivosCollectionIdentifiers.push(grupoActivosIdentifier);
        return true;
      });
      return [...grupoActivosToAdd, ...grupoActivosCollection];
    }
    return grupoActivosCollection;
  }

  setCriticidad(editForm: GrupoActivosFormGroup): void {
    const disponibilidad: IGrupoActivos['disponibilidad'] = editForm.get('disponibilidad')?.value;
    const integridad: IGrupoActivos['integridad'] = editForm.get('integridad')?.value;
    const condidencialidad: IGrupoActivos['confidencialidad'] = editForm.get('confidencialidad')?.value;
    editForm.get('criticidad')?.setValue(this.loadCriticidad(disponibilidad, integridad, condidencialidad));
  }

  protected loadCriticidad(
    disponibilidad: IGrupoActivos['disponibilidad'],
    integridad: IGrupoActivos['integridad'],
    condidencialidad: IGrupoActivos['criticidad'],
  ): IGrupoActivos['criticidad'] {
    if (disponibilidad === 'HIGH' || integridad === 'HIGH' || condidencialidad === 'HIGH') {
      return 'HIGH';
    }
    if (disponibilidad === 'MEDIUM' || integridad === 'MEDIUM' || condidencialidad === 'MEDIUM') {
      return 'MEDIUM';
    }
    return 'LOW';
  }
}
