import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActivoInformacion } from '../activo-informacion.model';
import { ActivoInformacionService } from '../service/activo-informacion.service';

const activoInformacionResolve = (route: ActivatedRouteSnapshot): Observable<null | IActivoInformacion> => {
  const id = route.params.id;
  if (id) {
    return inject(ActivoInformacionService)
      .find(id)
      .pipe(
        mergeMap((activoInformacion: HttpResponse<IActivoInformacion>) => {
          if (activoInformacion.body) {
            return of(activoInformacion.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default activoInformacionResolve;
