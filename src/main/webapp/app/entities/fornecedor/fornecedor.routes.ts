import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FornecedorComponent } from './list/fornecedor.component';
import { FornecedorDetailComponent } from './detail/fornecedor-detail.component';
import { FornecedorUpdateComponent } from './update/fornecedor-update.component';
import FornecedorResolve from './route/fornecedor-routing-resolve.service';

const fornecedorRoute: Routes = [
  {
    path: '',
    component: FornecedorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
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
    path: ':id/edit',
    component: FornecedorUpdateComponent,
    resolve: {
      fornecedor: FornecedorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default fornecedorRoute;
