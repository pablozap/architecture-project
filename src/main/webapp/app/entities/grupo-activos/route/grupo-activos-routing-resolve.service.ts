import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoActivos } from '../grupo-activos.model';
import { GrupoActivosService } from '../service/grupo-activos.service';

const grupoActivosResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoActivos> => {
  const id = route.params.id;
  if (id) {
    return inject(GrupoActivosService)
      .find(id)
      .pipe(
        mergeMap((grupoActivos: HttpResponse<IGrupoActivos>) => {
          if (grupoActivos.body) {
            return of(grupoActivos.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default grupoActivosResolve;
