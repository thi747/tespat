import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MovimentacaoComponent } from './list/movimentacao.component';
import { MovimentacaoDetailComponent } from './detail/movimentacao-detail.component';
import { MovimentacaoUpdateComponent } from './update/movimentacao-update.component';
import MovimentacaoResolve from './route/movimentacao-routing-resolve.service';

const movimentacaoRoute: Routes = [
  {
    path: '',
    component: MovimentacaoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MovimentacaoDetailComponent,
    resolve: {
      movimentacao: MovimentacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MovimentacaoUpdateComponent,
    resolve: {
      movimentacao: MovimentacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MovimentacaoUpdateComponent,
    resolve: {
      movimentacao: MovimentacaoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default movimentacaoRoute;
