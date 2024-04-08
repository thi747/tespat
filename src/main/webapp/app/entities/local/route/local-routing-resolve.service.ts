import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocal } from '../local.model';
import { LocalService } from '../service/local.service';

const localResolve = (route: ActivatedRouteSnapshot): Observable<null | ILocal> => {
  const id = route.params['id'];
  if (id) {
    return inject(LocalService)
      .find(id)
      .pipe(
        mergeMap((local: HttpResponse<ILocal>) => {
          if (local.body) {
            return of(local.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default localResolve;
