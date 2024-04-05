import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMovimentacao } from '../movimentacao.model';
import { MovimentacaoService } from '../service/movimentacao.service';

const movimentacaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IMovimentacao> => {
  const id = route.params['id'];
  if (id) {
    return inject(MovimentacaoService)
      .find(id)
      .pipe(
        mergeMap((movimentacao: HttpResponse<IMovimentacao>) => {
          if (movimentacao.body) {
            return of(movimentacao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default movimentacaoResolve;
