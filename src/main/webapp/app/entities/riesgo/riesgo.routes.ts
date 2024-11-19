import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import RiesgoResolve from './route/riesgo-routing-resolve.service';

const riesgoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/riesgo.component').then(m => m.RiesgoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/riesgo-detail.component').then(m => m.RiesgoDetailComponent),
    resolve: {
      riesgo: RiesgoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/riesgo-update.component').then(m => m.RiesgoUpdateComponent),
    resolve: {
      riesgo: RiesgoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/riesgo-update.component').then(m => m.RiesgoUpdateComponent),
    resolve: {
      riesgo: RiesgoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default riesgoRoute;
