import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoa } from '../pessoa.model';
import { PessoaService } from '../service/pessoa.service';

const pessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(PessoaService)
      .find(id)
      .pipe(
        mergeMap((pessoa: HttpResponse<IPessoa>) => {
          if (pessoa.body) {
            return of(pessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pessoaResolve;
