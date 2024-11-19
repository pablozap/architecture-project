import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVulnerabilidades } from '../vulnerabilidades.model';
import { VulnerabilidadesService } from '../service/vulnerabilidades.service';

const vulnerabilidadesResolve = (route: ActivatedRouteSnapshot): Observable<null | IVulnerabilidades> => {
  const id = route.params.id;
  if (id) {
    return inject(VulnerabilidadesService)
      .find(id)
      .pipe(
        mergeMap((vulnerabilidades: HttpResponse<IVulnerabilidades>) => {
          if (vulnerabilidades.body) {
            return of(vulnerabilidades.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default vulnerabilidadesResolve;
