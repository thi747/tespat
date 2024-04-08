import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoria, NewCategoria } from '../categoria.model';

export type EntityResponseType = HttpResponse<ICategoria>;
export type EntityArrayResponseType = HttpResponse<ICategoria[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorias');

  create(categoria: NewCategoria): Observable<EntityResponseType> {
    return this.http.post<ICategoria>(this.resourceUrl, categoria, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICategoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriaIdentifier(categoria: Pick<ICategoria, 'nome'>): string {
    return categoria.nome;
  }

  compareCategoria(o1: Pick<ICategoria, 'nome'> | null, o2: Pick<ICategoria, 'nome'> | null): boolean {
    return o1 && o2 ? this.getCategoriaIdentifier(o1) === this.getCategoriaIdentifier(o2) : o1 === o2;
  }

  addCategoriaToCollectionIfMissing<Type extends Pick<ICategoria, 'nome'>>(
    categoriaCollection: Type[],
    ...categoriasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorias: Type[] = categoriasToCheck.filter(isPresent);
    if (categorias.length > 0) {
      const categoriaCollectionIdentifiers = categoriaCollection.map(categoriaItem => this.getCategoriaIdentifier(categoriaItem));
      const categoriasToAdd = categorias.filter(categoriaItem => {
        const categoriaIdentifier = this.getCategoriaIdentifier(categoriaItem);
        if (categoriaCollectionIdentifiers.includes(categoriaIdentifier)) {
          return false;
        }
        categoriaCollectionIdentifiers.push(categoriaIdentifier);
        return true;
      });
      return [...categoriasToAdd, ...categoriaCollection];
    }
    return categoriaCollection;
  }
}
