import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFornecedor } from '../fornecedor.model';
import { FornecedorService } from '../service/fornecedor.service';

const fornecedorResolve = (route: ActivatedRouteSnapshot): Observable<null | IFornecedor> => {
  const id = route.params['id'];
  if (id) {
    return inject(FornecedorService)
      .find(id)
      .pipe(
        mergeMap((fornecedor: HttpResponse<IFornecedor>) => {
          if (fornecedor.body) {
            return of(fornecedor.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fornecedorResolve;
