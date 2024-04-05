import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LocalComponent } from './list/local.component';
import { LocalDetailComponent } from './detail/local-detail.component';
import { LocalUpdateComponent } from './update/local-update.component';
import LocalResolve from './route/local-routing-resolve.service';

const localRoute: Routes = [
  {
    path: '',
    component: LocalComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nome/view',
    component: LocalDetailComponent,
    resolve: {
      local: LocalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':nome/edit',
    component: LocalUpdateComponent,
    resolve: {
      local: LocalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default localRoute;
