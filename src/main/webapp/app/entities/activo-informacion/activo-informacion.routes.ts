import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ActivoInformacionResolve from './route/activo-informacion-routing-resolve.service';

const activoInformacionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/activo-informacion.component').then(m => m.ActivoInformacionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/activo-informacion-detail.component').then(m => m.ActivoInformacionDetailComponent),
    resolve: {
      activoInformacion: ActivoInformacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/activo-informacion-update.component').then(m => m.ActivoInformacionUpdateComponent),
    resolve: {
      activoInformacion: ActivoInformacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/activo-informacion-update.component').then(m => m.ActivoInformacionUpdateComponent),
    resolve: {
      activoInformacion: ActivoInformacionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default activoInformacionRoute;
