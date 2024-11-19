import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAmenazas } from '../amenazas.model';
import { AmenazasService } from '../service/amenazas.service';

const amenazasResolve = (route: ActivatedRouteSnapshot): Observable<null | IAmenazas> => {
  const id = route.params.id;
  if (id) {
    return inject(AmenazasService)
      .find(id)
      .pipe(
        mergeMap((amenazas: HttpResponse<IAmenazas>) => {
          if (amenazas.body) {
            return of(amenazas.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default amenazasResolve;
