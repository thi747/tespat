import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPessoa, NewPessoa } from '../pessoa.model';

export type PartialUpdatePessoa = Partial<IPessoa> & Pick<IPessoa, 'usuario'>;

export type EntityResponseType = HttpResponse<IPessoa>;
export type EntityArrayResponseType = HttpResponse<IPessoa[]>;

@Injectable({ providedIn: 'root' })
export class PessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pessoas');

  create(pessoa: NewPessoa): Observable<EntityResponseType> {
    return this.http.post<IPessoa>(this.resourceUrl, pessoa, { observe: 'response' });
  }

  update(pessoa: IPessoa): Observable<EntityResponseType> {
    return this.http.put<IPessoa>(`${this.resourceUrl}/${this.getPessoaIdentifier(pessoa)}`, pessoa, { observe: 'response' });
  }

  partialUpdate(pessoa: PartialUpdatePessoa): Observable<EntityResponseType> {
    return this.http.patch<IPessoa>(`${this.resourceUrl}/${this.getPessoaIdentifier(pessoa)}`, pessoa, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPessoaIdentifier(pessoa: Pick<IPessoa, 'usuario'>): string {
    return pessoa.usuario;
  }

  comparePessoa(o1: Pick<IPessoa, 'usuario'> | null, o2: Pick<IPessoa, 'usuario'> | null): boolean {
    return o1 && o2 ? this.getPessoaIdentifier(o1) === this.getPessoaIdentifier(o2) : o1 === o2;
  }

  addPessoaToCollectionIfMissing<Type extends Pick<IPessoa, 'usuario'>>(
    pessoaCollection: Type[],
    ...pessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pessoas: Type[] = pessoasToCheck.filter(isPresent);
    if (pessoas.length > 0) {
      const pessoaCollectionIdentifiers = pessoaCollection.map(pessoaItem => this.getPessoaIdentifier(pessoaItem));
      const pessoasToAdd = pessoas.filter(pessoaItem => {
        const pessoaIdentifier = this.getPessoaIdentifier(pessoaItem);
        if (pessoaCollectionIdentifiers.includes(pessoaIdentifier)) {
          return false;
        }
        pessoaCollectionIdentifiers.push(pessoaIdentifier);
        return true;
      });
      return [...pessoasToAdd, ...pessoaCollection];
    }
    return pessoaCollection;
  }
}
