import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControles } from '../controles.model';
import { ControlesService } from '../service/controles.service';

const controlesResolve = (route: ActivatedRouteSnapshot): Observable<null | IControles> => {
  const id = route.params.id;
  if (id) {
    return inject(ControlesService)
      .find(id)
      .pipe(
        mergeMap((controles: HttpResponse<IControles>) => {
          if (controles.body) {
            return of(controles.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default controlesResolve;
