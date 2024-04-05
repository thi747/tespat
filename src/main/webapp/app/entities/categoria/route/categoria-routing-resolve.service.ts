import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoria } from '../categoria.model';
import { CategoriaService } from '../service/categoria.service';

const categoriaResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategoria> => {
  const id = route.params['id'];
  if (id) {
    return inject(CategoriaService)
      .find(id)
      .pipe(
        mergeMap((categoria: HttpResponse<ICategoria>) => {
          if (categoria.body) {
            return of(categoria.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default categoriaResolve;
