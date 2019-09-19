import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

type EntityResponseType = HttpResponse<IAchievementBehaviour>;
type EntityArrayResponseType = HttpResponse<IAchievementBehaviour[]>;

@Injectable({ providedIn: 'root' })
export class AchievementBehaviourService {
  public resourceUrl = SERVER_API_URL + 'api/achievement-behaviours';

  constructor(protected http: HttpClient) {}

  create(achievementBehaviour: IAchievementBehaviour): Observable<EntityResponseType> {
    return this.http.post<IAchievementBehaviour>(this.resourceUrl, achievementBehaviour, { observe: 'response' });
  }

  update(achievementBehaviour: IAchievementBehaviour): Observable<EntityResponseType> {
    return this.http.put<IAchievementBehaviour>(this.resourceUrl, achievementBehaviour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAchievementBehaviour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAchievementBehaviour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
