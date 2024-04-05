import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILocal, NewLocal } from '../local.model';

export type PartialUpdateLocal = Partial<ILocal> & Pick<ILocal, 'nome'>;

export type EntityResponseType = HttpResponse<ILocal>;
export type EntityArrayResponseType = HttpResponse<ILocal[]>;

@Injectable({ providedIn: 'root' })
export class LocalService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/locals');

  create(local: NewLocal): Observable<EntityResponseType> {
    return this.http.post<ILocal>(this.resourceUrl, local, { observe: 'response' });
  }

  update(local: ILocal): Observable<EntityResponseType> {
    return this.http.put<ILocal>(`${this.resourceUrl}/${this.getLocalIdentifier(local)}`, local, { observe: 'response' });
  }

  partialUpdate(local: PartialUpdateLocal): Observable<EntityResponseType> {
    return this.http.patch<ILocal>(`${this.resourceUrl}/${this.getLocalIdentifier(local)}`, local, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILocal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLocalIdentifier(local: Pick<ILocal, 'nome'>): string {
    return local.nome;
  }

  compareLocal(o1: Pick<ILocal, 'nome'> | null, o2: Pick<ILocal, 'nome'> | null): boolean {
    return o1 && o2 ? this.getLocalIdentifier(o1) === this.getLocalIdentifier(o2) : o1 === o2;
  }

  addLocalToCollectionIfMissing<Type extends Pick<ILocal, 'nome'>>(
    localCollection: Type[],
    ...localsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const locals: Type[] = localsToCheck.filter(isPresent);
    if (locals.length > 0) {
      const localCollectionIdentifiers = localCollection.map(localItem => this.getLocalIdentifier(localItem));
      const localsToAdd = locals.filter(localItem => {
        const localIdentifier = this.getLocalIdentifier(localItem);
        if (localCollectionIdentifiers.includes(localIdentifier)) {
          return false;
        }
        localCollectionIdentifiers.push(localIdentifier);
        return true;
      });
      return [...localsToAdd, ...localCollection];
    }
    return localCollection;
  }
}
