import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBem, NewBem } from '../bem.model';

export type PartialUpdateBem = Partial<IBem> & Pick<IBem, 'patrimonio'>;

type RestOf<T extends IBem | NewBem> = Omit<T, 'dataAquisicao'> & {
  dataAquisicao?: string | null;
};

export type RestBem = RestOf<IBem>;

export type NewRestBem = RestOf<NewBem>;

export type PartialUpdateRestBem = RestOf<PartialUpdateBem>;

export type EntityResponseType = HttpResponse<IBem>;
export type EntityArrayResponseType = HttpResponse<IBem[]>;

@Injectable({ providedIn: 'root' })
export class BemService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bems');

  create(bem: NewBem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bem);
    return this.http.post<RestBem>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(bem: IBem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bem);
    return this.http
      .put<RestBem>(`${this.resourceUrl}/${this.getBemIdentifier(bem)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(bem: PartialUpdateBem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bem);
    return this.http
      .patch<RestBem>(`${this.resourceUrl}/${this.getBemIdentifier(bem)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBemIdentifier(bem: Pick<IBem, 'patrimonio'>): number {
    return bem.patrimonio;
  }

  compareBem(o1: Pick<IBem, 'patrimonio'> | null, o2: Pick<IBem, 'patrimonio'> | null): boolean {
    return o1 && o2 ? this.getBemIdentifier(o1) === this.getBemIdentifier(o2) : o1 === o2;
  }

  addBemToCollectionIfMissing<Type extends Pick<IBem, 'patrimonio'>>(
    bemCollection: Type[],
    ...bemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bems: Type[] = bemsToCheck.filter(isPresent);
    if (bems.length > 0) {
      const bemCollectionIdentifiers = bemCollection.map(bemItem => this.getBemIdentifier(bemItem));
      const bemsToAdd = bems.filter(bemItem => {
        const bemIdentifier = this.getBemIdentifier(bemItem);
        if (bemCollectionIdentifiers.includes(bemIdentifier)) {
          return false;
        }
        bemCollectionIdentifiers.push(bemIdentifier);
        return true;
      });
      return [...bemsToAdd, ...bemCollection];
    }
    return bemCollection;
  }

  protected convertDateFromClient<T extends IBem | NewBem | PartialUpdateBem>(bem: T): RestOf<T> {
    return {
      ...bem,
      dataAquisicao: bem.dataAquisicao?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restBem: RestBem): IBem {
    return {
      ...restBem,
      dataAquisicao: restBem.dataAquisicao ? dayjs(restBem.dataAquisicao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBem>): HttpResponse<IBem> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBem[]>): HttpResponse<IBem[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
