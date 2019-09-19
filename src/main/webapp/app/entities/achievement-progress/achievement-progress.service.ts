import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';

type EntityResponseType = HttpResponse<IAchievementProgress>;
type EntityArrayResponseType = HttpResponse<IAchievementProgress[]>;

@Injectable({ providedIn: 'root' })
export class AchievementProgressService {
  public resourceUrl = SERVER_API_URL + 'api/achievement-progresses';

  constructor(protected http: HttpClient) {}

  create(achievementProgress: IAchievementProgress): Observable<EntityResponseType> {
    return this.http.post<IAchievementProgress>(this.resourceUrl, achievementProgress, { observe: 'response' });
  }

  update(achievementProgress: IAchievementProgress): Observable<EntityResponseType> {
    return this.http.put<IAchievementProgress>(this.resourceUrl, achievementProgress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAchievementProgress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAchievementProgress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
