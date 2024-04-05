import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMovimentacao, NewMovimentacao } from '../movimentacao.model';

export type PartialUpdateMovimentacao = Partial<IMovimentacao> & Pick<IMovimentacao, 'id'>;

type RestOf<T extends IMovimentacao | NewMovimentacao> = Omit<T, 'data'> & {
  data?: string | null;
};

export type RestMovimentacao = RestOf<IMovimentacao>;

export type NewRestMovimentacao = RestOf<NewMovimentacao>;

export type PartialUpdateRestMovimentacao = RestOf<PartialUpdateMovimentacao>;

export type EntityResponseType = HttpResponse<IMovimentacao>;
export type EntityArrayResponseType = HttpResponse<IMovimentacao[]>;

@Injectable({ providedIn: 'root' })
export class MovimentacaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/movimentacaos');

  create(movimentacao: NewMovimentacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimentacao);
    return this.http
      .post<RestMovimentacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(movimentacao: IMovimentacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimentacao);
    return this.http
      .put<RestMovimentacao>(`${this.resourceUrl}/${this.getMovimentacaoIdentifier(movimentacao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(movimentacao: PartialUpdateMovimentacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(movimentacao);
    return this.http
      .patch<RestMovimentacao>(`${this.resourceUrl}/${this.getMovimentacaoIdentifier(movimentacao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMovimentacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMovimentacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMovimentacaoIdentifier(movimentacao: Pick<IMovimentacao, 'id'>): number {
    return movimentacao.id;
  }

  compareMovimentacao(o1: Pick<IMovimentacao, 'id'> | null, o2: Pick<IMovimentacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getMovimentacaoIdentifier(o1) === this.getMovimentacaoIdentifier(o2) : o1 === o2;
  }

  addMovimentacaoToCollectionIfMissing<Type extends Pick<IMovimentacao, 'id'>>(
    movimentacaoCollection: Type[],
    ...movimentacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const movimentacaos: Type[] = movimentacaosToCheck.filter(isPresent);
    if (movimentacaos.length > 0) {
      const movimentacaoCollectionIdentifiers = movimentacaoCollection.map(movimentacaoItem =>
        this.getMovimentacaoIdentifier(movimentacaoItem),
      );
      const movimentacaosToAdd = movimentacaos.filter(movimentacaoItem => {
        const movimentacaoIdentifier = this.getMovimentacaoIdentifier(movimentacaoItem);
        if (movimentacaoCollectionIdentifiers.includes(movimentacaoIdentifier)) {
          return false;
        }
        movimentacaoCollectionIdentifiers.push(movimentacaoIdentifier);
        return true;
      });
      return [...movimentacaosToAdd, ...movimentacaoCollection];
    }
    return movimentacaoCollection;
  }

  protected convertDateFromClient<T extends IMovimentacao | NewMovimentacao | PartialUpdateMovimentacao>(movimentacao: T): RestOf<T> {
    return {
      ...movimentacao,
      data: movimentacao.data?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restMovimentacao: RestMovimentacao): IMovimentacao {
    return {
      ...restMovimentacao,
      data: restMovimentacao.data ? dayjs(restMovimentacao.data) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMovimentacao>): HttpResponse<IMovimentacao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMovimentacao[]>): HttpResponse<IMovimentacao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
