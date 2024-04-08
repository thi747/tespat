import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CategoriaComponent } from './list/categoria.component';
import { CategoriaDetailComponent } from './detail/categoria-detail.component';
import { CategoriaUpdateComponent } from './update/categoria-update.component';
import CategoriaResolve from './route/categoria-routing-resolve.service';

const categoriaRoute: Routes = [
  {
    path: '',
    component: CategoriaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriaDetailComponent,
    resolve: {
      categoria: CategoriaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriaUpdateComponent,
    resolve: {
      categoria: CategoriaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categoriaRoute;
