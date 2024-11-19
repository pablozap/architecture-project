import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'proyectoArquitecturaApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
    title: 'proyectoArquitecturaApp.adminAuthority.home.title',
  },
  {
    path: 'activo-informacion',
    data: { pageTitle: 'proyectoArquitecturaApp.activoInformacion.home.title' },
    loadChildren: () => import('./activo-informacion/activo-informacion.routes'),
    title: 'proyectoArquitecturaApp.activoInformacion.home.title',
  },
  {
    path: 'amenazas',
    data: { pageTitle: 'proyectoArquitecturaApp.amenazas.home.title' },
    loadChildren: () => import('./amenazas/amenazas.routes'),
    title: 'proyectoArquitecturaApp.amenazas.home.title',
  },
  {
    path: 'controles',
    data: { pageTitle: 'proyectoArquitecturaApp.controles.home.title' },
    loadChildren: () => import('./controles/controles.routes'),
    title: 'proyectoArquitecturaApp.controles.home.title',
  },
  {
    path: 'grupo-activos',
    data: { pageTitle: 'proyectoArquitecturaApp.grupoActivos.home.title' },
    loadChildren: () => import('./grupo-activos/grupo-activos.routes'),
    title: 'proyectoArquitecturaApp.grupoActivos.home.title',
  },
  {
    path: 'riesgo',
    data: { pageTitle: 'proyectoArquitecturaApp.riesgo.home.title' },
    loadChildren: () => import('./riesgo/riesgo.routes'),
    title: 'proyectoArquitecturaApp.riesgo.home.title',
  },
  {
    path: 'vulnerabilidades',
    data: { pageTitle: 'proyectoArquitecturaApp.vulnerabilidades.home.title' },
    loadChildren: () => import('./vulnerabilidades/vulnerabilidades.routes'),
    title: 'proyectoArquitecturaApp.vulnerabilidades.home.title',
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
