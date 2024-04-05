import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PessoaComponent } from './list/pessoa.component';
import { PessoaDetailComponent } from './detail/pessoa-detail.component';
import { PessoaUpdateComponent } from './update/pessoa-update.component';
import PessoaResolve from './route/pessoa-routing-resolve.service';

const pessoaRoute: Routes = [
  {
    path: '',
    component: PessoaComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':usuario/view',
    component: PessoaDetailComponent,
    resolve: {
      pessoa: PessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoaUpdateComponent,
    resolve: {
      pessoa: PessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':usuario/edit',
    component: PessoaUpdateComponent,
    resolve: {
      pessoa: PessoaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pessoaRoute;
