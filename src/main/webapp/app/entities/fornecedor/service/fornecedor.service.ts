import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFornecedor, NewFornecedor } from '../fornecedor.model';

export type PartialUpdateFornecedor = Partial<IFornecedor> & Pick<IFornecedor, 'id'>;

export type EntityResponseType = HttpResponse<IFornecedor>;
export type EntityArrayResponseType = HttpResponse<IFornecedor[]>;

@Injectable({ providedIn: 'root' })
export class FornecedorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fornecedores');

  create(fornecedor: NewFornecedor): Observable<EntityResponseType> {
    return this.http.post<IFornecedor>(this.resourceUrl, fornecedor, { observe: 'response' });
  }

  update(fornecedor: IFornecedor): Observable<EntityResponseType> {
    return this.http.put<IFornecedor>(`${this.resourceUrl}/${this.getFornecedorIdentifier(fornecedor)}`, fornecedor, {
      observe: 'response',
    });
  }

  partialUpdate(fornecedor: PartialUpdateFornecedor): Observable<EntityResponseType> {
    return this.http.patch<IFornecedor>(`${this.resourceUrl}/${this.getFornecedorIdentifier(fornecedor)}`, fornecedor, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFornecedor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFornecedor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFornecedorIdentifier(fornecedor: Pick<IFornecedor, 'id'>): number {
    return fornecedor.id;
  }

  compareFornecedor(o1: Pick<IFornecedor, 'id'> | null, o2: Pick<IFornecedor, 'id'> | null): boolean {
    return o1 && o2 ? this.getFornecedorIdentifier(o1) === this.getFornecedorIdentifier(o2) : o1 === o2;
  }

  addFornecedorToCollectionIfMissing<Type extends Pick<IFornecedor, 'id'>>(
    fornecedorCollection: Type[],
    ...fornecedoresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fornecedores: Type[] = fornecedoresToCheck.filter(isPresent);
    if (fornecedores.length > 0) {
      const fornecedorCollectionIdentifiers = fornecedorCollection.map(fornecedorItem => this.getFornecedorIdentifier(fornecedorItem));
      const fornecedoresToAdd = fornecedores.filter(fornecedorItem => {
        const fornecedorIdentifier = this.getFornecedorIdentifier(fornecedorItem);
        if (fornecedorCollectionIdentifiers.includes(fornecedorIdentifier)) {
          return false;
        }
        fornecedorCollectionIdentifiers.push(fornecedorIdentifier);
        return true;
      });
      return [...fornecedoresToAdd, ...fornecedorCollection];
    }
    return fornecedorCollection;
  }
}
