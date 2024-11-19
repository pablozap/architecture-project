import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAmenazas, NewAmenazas } from '../amenazas.model';

export type PartialUpdateAmenazas = Partial<IAmenazas> & Pick<IAmenazas, 'id'>;

export type EntityResponseType = HttpResponse<IAmenazas>;
export type EntityArrayResponseType = HttpResponse<IAmenazas[]>;

@Injectable({ providedIn: 'root' })
export class AmenazasService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/amenazas');

  create(amenazas: NewAmenazas): Observable<EntityResponseType> {
    return this.http.post<IAmenazas>(this.resourceUrl, amenazas, { observe: 'response' });
  }

  update(amenazas: IAmenazas): Observable<EntityResponseType> {
    return this.http.put<IAmenazas>(`${this.resourceUrl}/${this.getAmenazasIdentifier(amenazas)}`, amenazas, { observe: 'response' });
  }

  partialUpdate(amenazas: PartialUpdateAmenazas): Observable<EntityResponseType> {
    return this.http.patch<IAmenazas>(`${this.resourceUrl}/${this.getAmenazasIdentifier(amenazas)}`, amenazas, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAmenazas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAmenazas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAmenazasIdentifier(amenazas: Pick<IAmenazas, 'id'>): number {
    return amenazas.id;
  }

  compareAmenazas(o1: Pick<IAmenazas, 'id'> | null, o2: Pick<IAmenazas, 'id'> | null): boolean {
    return o1 && o2 ? this.getAmenazasIdentifier(o1) === this.getAmenazasIdentifier(o2) : o1 === o2;
  }

  addAmenazasToCollectionIfMissing<Type extends Pick<IAmenazas, 'id'>>(
    amenazasCollection: Type[],
    ...amenazasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const amenazas: Type[] = amenazasToCheck.filter(isPresent);
    if (amenazas.length > 0) {
      const amenazasCollectionIdentifiers = amenazasCollection.map(amenazasItem => this.getAmenazasIdentifier(amenazasItem));
      const amenazasToAdd = amenazas.filter(amenazasItem => {
        const amenazasIdentifier = this.getAmenazasIdentifier(amenazasItem);
        if (amenazasCollectionIdentifiers.includes(amenazasIdentifier)) {
          return false;
        }
        amenazasCollectionIdentifiers.push(amenazasIdentifier);
        return true;
      });
      return [...amenazasToAdd, ...amenazasCollection];
    }
    return amenazasCollection;
  }
}
