import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import VulnerabilidadesResolve from './route/vulnerabilidades-routing-resolve.service';

const vulnerabilidadesRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/vulnerabilidades.component').then(m => m.VulnerabilidadesComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/vulnerabilidades-detail.component').then(m => m.VulnerabilidadesDetailComponent),
    resolve: {
      vulnerabilidades: VulnerabilidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/vulnerabilidades-update.component').then(m => m.VulnerabilidadesUpdateComponent),
    resolve: {
      vulnerabilidades: VulnerabilidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/vulnerabilidades-update.component').then(m => m.VulnerabilidadesUpdateComponent),
    resolve: {
      vulnerabilidades: VulnerabilidadesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default vulnerabilidadesRoute;
