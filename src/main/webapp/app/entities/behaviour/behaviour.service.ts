import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBehaviour } from 'app/shared/model/behaviour.model';

type EntityResponseType = HttpResponse<IBehaviour>;
type EntityArrayResponseType = HttpResponse<IBehaviour[]>;

@Injectable({ providedIn: 'root' })
export class BehaviourService {
  public resourceUrl = SERVER_API_URL + 'api/behaviours';

  constructor(protected http: HttpClient) {}

  create(behaviour: IBehaviour): Observable<EntityResponseType> {
    return this.http.post<IBehaviour>(this.resourceUrl, behaviour, { observe: 'response' });
  }

  update(behaviour: IBehaviour): Observable<EntityResponseType> {
    return this.http.put<IBehaviour>(this.resourceUrl, behaviour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBehaviour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBehaviour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
