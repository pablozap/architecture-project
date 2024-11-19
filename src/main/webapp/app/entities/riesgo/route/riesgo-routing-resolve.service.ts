import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRiesgo } from '../riesgo.model';
import { RiesgoService } from '../service/riesgo.service';

const riesgoResolve = (route: ActivatedRouteSnapshot): Observable<null | IRiesgo> => {
  const id = route.params.id;
  if (id) {
    return inject(RiesgoService)
      .find(id)
      .pipe(
        mergeMap((riesgo: HttpResponse<IRiesgo>) => {
          if (riesgo.body) {
            return of(riesgo.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default riesgoResolve;
