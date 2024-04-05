import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBem } from '../bem.model';
import { BemService } from '../service/bem.service';

const bemResolve = (route: ActivatedRouteSnapshot): Observable<null | IBem> => {
  const id = route.params['patrimonio'];
  if (id) {
    return inject(BemService)
      .find(id)
      .pipe(
        mergeMap((bem: HttpResponse<IBem>) => {
          if (bem.body) {
            return of(bem.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bemResolve;
