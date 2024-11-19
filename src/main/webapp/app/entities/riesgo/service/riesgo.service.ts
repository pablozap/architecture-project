import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRiesgo, NewRiesgo } from '../riesgo.model';
import { RiesgoFormGroup } from '../update/riesgo-form.service';
import { TipoRiesgo } from '../../enumerations/tipo-riesgo.model';

export type PartialUpdateRiesgo = Partial<IRiesgo> & Pick<IRiesgo, 'id'>;

type RestOf<T extends IRiesgo | NewRiesgo> = Omit<T, 'fechaImplementacion' | 'fechaMonitoreo'> & {
  fechaImplementacion?: string | null;
  fechaMonitoreo?: string | null;
};

export type RestRiesgo = RestOf<IRiesgo>;

export type NewRestRiesgo = RestOf<NewRiesgo>;

export type PartialUpdateRestRiesgo = RestOf<PartialUpdateRiesgo>;

export type EntityResponseType = HttpResponse<IRiesgo>;
export type EntityArrayResponseType = HttpResponse<IRiesgo[]>;

@Injectable({ providedIn: 'root' })
export class RiesgoService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/riesgos');

  create(riesgo: NewRiesgo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(riesgo);
    return this.http
      .post<RestRiesgo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(riesgo: IRiesgo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(riesgo);
    return this.http
      .put<RestRiesgo>(`${this.resourceUrl}/${this.getRiesgoIdentifier(riesgo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(riesgo: PartialUpdateRiesgo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(riesgo);
    return this.http
      .patch<RestRiesgo>(`${this.resourceUrl}/${this.getRiesgoIdentifier(riesgo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRiesgo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRiesgo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRiesgoIdentifier(riesgo: Pick<IRiesgo, 'id'>): number {
    return riesgo.id;
  }

  compareRiesgo(o1: Pick<IRiesgo, 'id'> | null, o2: Pick<IRiesgo, 'id'> | null): boolean {
    return o1 && o2 ? this.getRiesgoIdentifier(o1) === this.getRiesgoIdentifier(o2) : o1 === o2;
  }

  updateDescription(editForm: RiesgoFormGroup): void {
    const activos = editForm.get('activos')?.value?.map(obj => (obj.nombre ? ' ' + obj.nombre.toString() : null));
    let tipoRiesgo: TipoRiesgo | null;
    // TODO improve management of enum values in forms
    switch (editForm.get('tipoRiesgo')?.value) {
      case 'INTEGRITY':
        tipoRiesgo = TipoRiesgo.INTEGRITY;
        break;
      case 'CONFIDENTIALITY':
        tipoRiesgo = TipoRiesgo.CONFIDENTIALITY;
        break;
      case 'AVAILABILITY':
        tipoRiesgo = TipoRiesgo.AVAILABILITY;
        break;
      default:
        tipoRiesgo = null;
    }
    const amenaza = editForm.get('amenaza')?.value?.nombre;
    const vulnerabilidad = editForm.get('vulnerabilidad')?.value?.nombre;
    editForm
      .get('descripcion')!
      .setValue(`Posible ${tipoRiesgo} del/los activos${activos?.toString()}, por accion de ${amenaza}, ${vulnerabilidad}`);
  }

  updateZonaRiesgo(editForm: RiesgoFormGroup): void {
    const frecuencia = editForm.get('frecuencia')?.value;
    let probabilidadInherente: IRiesgo['probabilidadInherente'];
    if (frecuencia != null && frecuencia > 0) {
      probabilidadInherente = this.calculateProbabilidadInherente(frecuencia);
      editForm.get('probabilidadInherente')?.setValue(probabilidadInherente);
    }
    let percentageFromProbabilidadInherente = 0;
    switch (probabilidadInherente) {
      case 'HIGHEST':
        percentageFromProbabilidadInherente = 1;
        break;
      case 'HIGH':
        percentageFromProbabilidadInherente = 0.8;
        break;
      case 'MEDIUM':
        percentageFromProbabilidadInherente = 0.6;
        break;
      case 'LOW':
        percentageFromProbabilidadInherente = 0.4;
        break;
      case 'LOWEST':
        percentageFromProbabilidadInherente = 0.2;
        break;
    }
    const afectacionEconomica = editForm.get('afectacionEconomica')?.value;
    let percentageFromImpactoInherente = 0;
    switch (afectacionEconomica) {
      case 'HIGHEST':
        percentageFromImpactoInherente = 1;
        break;
      case 'HIGH':
        percentageFromImpactoInherente = 0.8;
        break;
      case 'MEDIUM':
        percentageFromImpactoInherente = 0.6;
        break;
      case 'LOW':
        percentageFromImpactoInherente = 0.4;
        break;
      case 'LOWEST':
        percentageFromImpactoInherente = 0.2;
        break;
    }
    editForm.get('impactoReputacional')?.setValue(afectacionEconomica);
    editForm.get('impactoInherente')?.setValue(afectacionEconomica);
    editForm.get('zonaRiesgo')?.setValue(this.calculateZonaRiesgo(afectacionEconomica, probabilidadInherente));
    const tipoControl: IRiesgo['tipoControl'] = editForm.get('tipoControl')?.value;
    let percentageTipoControl = 0;
    switch (tipoControl) {
      case 'PREVENTIVE':
        percentageTipoControl = 0.25;
        break;
      case 'DETECTIVE':
        percentageTipoControl = 0.15;
        break;
      case 'CORRECTIVE':
        percentageTipoControl = 0.1;
        break;
    }
    const implementacionControl: IRiesgo['implementacion'] = editForm.get('implementacion')?.value;
    let percentageImplementacionControl = 0;
    switch (implementacionControl) {
      case 'AUTOMATIC':
        percentageImplementacionControl = 0.25;
        break;
      case 'MANUAL':
        percentageImplementacionControl = 0.15;
        break;
    }
    const percentageCalificacionControl = percentageTipoControl + percentageImplementacionControl;
    editForm.get('calificacionControl')?.setValue(percentageCalificacionControl * 100);
    const afectacion: IRiesgo['afectacion'] = editForm.get('afectacion')?.value;
    let percentageProbabilidadResidual;
    if (afectacion === 'PROBABILITY') {
      percentageProbabilidadResidual =
        percentageFromProbabilidadInherente - percentageFromProbabilidadInherente * percentageCalificacionControl;
    } else {
      percentageProbabilidadResidual = percentageFromProbabilidadInherente;
    }
    percentageProbabilidadResidual = percentageProbabilidadResidual * 100;
    editForm.get('probabilidad')?.setValue(percentageProbabilidadResidual);
    let percentageImpactoResidual: number;
    if (afectacion === 'IMPACT') {
      percentageImpactoResidual = percentageFromImpactoInherente - percentageFromImpactoInherente * percentageCalificacionControl;
    } else {
      percentageImpactoResidual = percentageFromImpactoInherente;
    }
    percentageImpactoResidual = percentageImpactoResidual * 100;
    editForm.get('impacto')?.setValue(percentageImpactoResidual);
    const probabilidadResidualFinal: IRiesgo['probabilidadResidualFinal'] =
      this.calculateProbabilidadOrImpactoResidual(percentageProbabilidadResidual);
    editForm.get('probabilidadResidualFinal')?.setValue(probabilidadResidualFinal);
    const impactoResidualFinal: IRiesgo['impactoResidualFinal'] = this.calculateProbabilidadOrImpactoResidual(percentageImpactoResidual);
    editForm.get('impactoResidualFinal')?.setValue(impactoResidualFinal);
    editForm.get('zonaDeRiesgoFinal')?.setValue(this.calculateZonaRiesgo(probabilidadResidualFinal, impactoResidualFinal));
  }

  addRiesgoToCollectionIfMissing<Type extends Pick<IRiesgo, 'id'>>(
    riesgoCollection: Type[],
    ...riesgosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const riesgos: Type[] = riesgosToCheck.filter(isPresent);
    if (riesgos.length > 0) {
      const riesgoCollectionIdentifiers = riesgoCollection.map(riesgoItem => this.getRiesgoIdentifier(riesgoItem));
      const riesgosToAdd = riesgos.filter(riesgoItem => {
        const riesgoIdentifier = this.getRiesgoIdentifier(riesgoItem);
        if (riesgoCollectionIdentifiers.includes(riesgoIdentifier)) {
          return false;
        }
        riesgoCollectionIdentifiers.push(riesgoIdentifier);
        return true;
      });
      return [...riesgosToAdd, ...riesgoCollection];
    }
    return riesgoCollection;
  }

  protected convertDateFromClient<T extends IRiesgo | NewRiesgo | PartialUpdateRiesgo>(riesgo: T): RestOf<T> {
    return {
      ...riesgo,
      fechaImplementacion: riesgo.fechaImplementacion?.format(DATE_FORMAT) ?? null,
      fechaMonitoreo: riesgo.fechaMonitoreo?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRiesgo: RestRiesgo): IRiesgo {
    return {
      ...restRiesgo,
      fechaImplementacion: restRiesgo.fechaImplementacion ? dayjs(restRiesgo.fechaImplementacion) : undefined,
      fechaMonitoreo: restRiesgo.fechaMonitoreo ? dayjs(restRiesgo.fechaMonitoreo) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRiesgo>): HttpResponse<IRiesgo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRiesgo[]>): HttpResponse<IRiesgo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }

  protected calculateProbabilidadInherente(frecuencia: number): IRiesgo['probabilidadInherente'] {
    if (frecuencia > 0 && frecuencia <= 2) {
      return 'LOWEST';
    }
    if (frecuencia > 2 && frecuencia <= 24) {
      return 'LOW';
    }
    if (frecuencia > 24 && frecuencia <= 500) {
      return 'MEDIUM';
    }
    if (frecuencia > 500 && frecuencia <= 5000) {
      return 'HIGH';
    }
    return 'HIGHEST';
  }

  protected calculateZonaRiesgo(
    probabilidad: IRiesgo['probabilidadInherente'],
    impacto: IRiesgo['impactoInherente'],
  ): IRiesgo['zonaRiesgo'] {
    if (probabilidad === 'HIGHEST' || impacto === 'HIGHEST') {
      return 'HIGHEST';
    }
    if (probabilidad === 'HIGH' || impacto === 'HIGH') {
      return 'HIGH';
    }
    if (probabilidad === 'MEDIUM' || impacto === 'MEDIUM') {
      return 'MEDIUM';
    }
    if (probabilidad === 'LOW' || impacto === 'LOW') {
      return 'LOW';
    }
    return 'LOWEST';
  }

  protected calculateProbabilidadOrImpactoResidual(percentageResidual: number): IRiesgo['probabilidadResidualFinal'] {
    if (percentageResidual <= 100 && percentageResidual > 80) {
      return 'HIGHEST';
    }
    if (percentageResidual <= 80 && percentageResidual > 60) {
      return 'HIGH';
    }
    if (percentageResidual <= 60 && percentageResidual > 40) {
      return 'MEDIUM';
    }
    if (percentageResidual <= 40 && percentageResidual > 20) {
      return 'LOW';
    }
    return 'LOWEST';
  }
}
