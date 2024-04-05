import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BemComponent } from './list/bem.component';
import { BemDetailComponent } from './detail/bem-detail.component';
import { BemUpdateComponent } from './update/bem-update.component';
import BemResolve from './route/bem-routing-resolve.service';

const bemRoute: Routes = [
  {
    path: '',
    component: BemComponent,
    data: {
      defaultSort: 'patrimonio,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':patrimonio/view',
    component: BemDetailComponent,
    resolve: {
      bem: BemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BemUpdateComponent,
    resolve: {
      bem: BemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':patrimonio/edit',
    component: BemUpdateComponent,
    resolve: {
      bem: BemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bemRoute;
