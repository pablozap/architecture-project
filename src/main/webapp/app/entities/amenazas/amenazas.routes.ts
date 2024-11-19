import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AmenazasResolve from './route/amenazas-routing-resolve.service';

const amenazasRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/amenazas.component').then(m => m.AmenazasComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/amenazas-detail.component').then(m => m.AmenazasDetailComponent),
    resolve: {
      amenazas: AmenazasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/amenazas-update.component').then(m => m.AmenazasUpdateComponent),
    resolve: {
      amenazas: AmenazasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/amenazas-update.component').then(m => m.AmenazasUpdateComponent),
    resolve: {
      amenazas: AmenazasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default amenazasRoute;
