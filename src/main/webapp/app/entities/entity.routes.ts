import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'tesPatApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'bem',
    data: { pageTitle: 'tesPatApp.bem.home.title' },
    loadChildren: () => import('./bem/bem.routes'),
  },
  {
    path: 'categoria',
    data: { pageTitle: 'tesPatApp.categoria.home.title' },
    loadChildren: () => import('./categoria/categoria.routes'),
  },
  {
    path: 'fornecedor',
    data: { pageTitle: 'tesPatApp.fornecedor.home.title' },
    loadChildren: () => import('./fornecedor/fornecedor.routes'),
  },
  {
    path: 'local',
    data: { pageTitle: 'tesPatApp.local.home.title' },
    loadChildren: () => import('./local/local.routes'),
  },
  {
    path: 'movimentacao',
    data: { pageTitle: 'tesPatApp.movimentacao.home.title' },
    loadChildren: () => import('./movimentacao/movimentacao.routes'),
  },
  {
    path: 'pessoa',
    data: { pageTitle: 'tesPatApp.pessoa.home.title' },
    loadChildren: () => import('./pessoa/pessoa.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
