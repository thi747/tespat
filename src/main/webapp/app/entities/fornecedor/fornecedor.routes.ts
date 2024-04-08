import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FornecedorComponent } from './list/fornecedor.component';
import { FornecedorDetailComponent } from './detail/fornecedor-detail.component';
import { FornecedorUpdateComponent } from './update/fornecedor-update.component';
import FornecedorResolve from './route/fornecedor-routing-resolve.service';

const fornecedorRoute: Routes = [
  {
    path: '',
    component: FornecedorComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nome/view',
    component: FornecedorDetailComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FornecedorUpdateComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nome/edit',
    component: FornecedorUpdateComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fornecedorRoute;
