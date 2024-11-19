import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActivoInformacion, NewActivoInformacion } from '../activo-informacion.model';

export type PartialUpdateActivoInformacion = Partial<IActivoInformacion> & Pick<IActivoInformacion, 'id'>;

type RestOf<T extends IActivoInformacion | NewActivoInformacion> = Omit<T, 'fecha' | 'fechaIngreso' | 'fechaRetiro'> & {
  fecha?: string | null;
  fechaIngreso?: string | null;
  fechaRetiro?: string | null;
};

export type RestActivoInformacion = RestOf<IActivoInformacion>;

export type NewRestActivoInformacion = RestOf<NewActivoInformacion>;

export type PartialUpdateRestActivoInformacion = RestOf<PartialUpdateActivoInformacion>;

export type EntityResponseType = HttpResponse<IActivoInformacion>;
export type EntityArrayResponseType = HttpResponse<IActivoInformacion[]>;

@Injectable({ providedIn: 'root' })
export class ActivoInformacionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/activo-informacions');

  create(activoInformacion: NewActivoInformacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activoInformacion);
    return this.http
      .post<RestActivoInformacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(activoInformacion: IActivoInformacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activoInformacion);
    return this.http
      .put<RestActivoInformacion>(`${this.resourceUrl}/${this.getActivoInformacionIdentifier(activoInformacion)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(activoInformacion: PartialUpdateActivoInformacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(activoInformacion);
    return this.http
      .patch<RestActivoInformacion>(`${this.resourceUrl}/${this.getActivoInformacionIdentifier(activoInformacion)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestActivoInformacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestActivoInformacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActivoInformacionIdentifier(activoInformacion: Pick<IActivoInformacion, 'id'>): number {
    return activoInformacion.id;
  }

  compareActivoInformacion(o1: Pick<IActivoInformacion, 'id'> | null, o2: Pick<IActivoInformacion, 'id'> | null): boolean {
    return o1 && o2 ? this.getActivoInformacionIdentifier(o1) === this.getActivoInformacionIdentifier(o2) : o1 === o2;
  }

  addActivoInformacionToCollectionIfMissing<Type extends Pick<IActivoInformacion, 'id'>>(
    activoInformacionCollection: Type[],
    ...activoInformacionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const activoInformacions: Type[] = activoInformacionsToCheck.filter(isPresent);
    if (activoInformacions.length > 0) {
      const activoInformacionCollectionIdentifiers = activoInformacionCollection.map(activoInformacionItem =>
        this.getActivoInformacionIdentifier(activoInformacionItem),
      );
      const activoInformacionsToAdd = activoInformacions.filter(activoInformacionItem => {
        const activoInformacionIdentifier = this.getActivoInformacionIdentifier(activoInformacionItem);
        if (activoInformacionCollectionIdentifiers.includes(activoInformacionIdentifier)) {
          return false;
        }
        activoInformacionCollectionIdentifiers.push(activoInformacionIdentifier);
        return true;
      });
      return [...activoInformacionsToAdd, ...activoInformacionCollection];
    }
    return activoInformacionCollection;
  }

  protected convertDateFromClient<T extends IActivoInformacion | NewActivoInformacion | PartialUpdateActivoInformacion>(
    activoInformacion: T,
  ): RestOf<T> {
    return {
      ...activoInformacion,
      fecha: activoInformacion.fecha?.format(DATE_FORMAT) ?? null,
      fechaIngreso: activoInformacion.fechaIngreso?.format(DATE_FORMAT) ?? null,
      fechaRetiro: activoInformacion.fechaRetiro?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restActivoInformacion: RestActivoInformacion): IActivoInformacion {
    return {
      ...restActivoInformacion,
      fecha: restActivoInformacion.fecha ? dayjs(restActivoInformacion.fecha) : undefined,
      fechaIngreso: restActivoInformacion.fechaIngreso ? dayjs(restActivoInformacion.fechaIngreso) : undefined,
      fechaRetiro: restActivoInformacion.fechaRetiro ? dayjs(restActivoInformacion.fechaRetiro) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestActivoInformacion>): HttpResponse<IActivoInformacion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestActivoInformacion[]>): HttpResponse<IActivoInformacion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
