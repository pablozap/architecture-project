import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import GrupoActivosResolve from './route/grupo-activos-routing-resolve.service';

const grupoActivosRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/grupo-activos.component').then(m => m.GrupoActivosComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/grupo-activos-detail.component').then(m => m.GrupoActivosDetailComponent),
    resolve: {
      grupoActivos: GrupoActivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/grupo-activos-update.component').then(m => m.GrupoActivosUpdateComponent),
    resolve: {
      grupoActivos: GrupoActivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/grupo-activos-update.component').then(m => m.GrupoActivosUpdateComponent),
    resolve: {
      grupoActivos: GrupoActivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default grupoActivosRoute;
