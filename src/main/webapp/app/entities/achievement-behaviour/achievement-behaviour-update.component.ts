import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAchievementBehaviour, AchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';
import { AchievementBehaviourService } from './achievement-behaviour.service';
import { IAchievement } from 'app/shared/model/achievement.model';
import { AchievementService } from 'app/entities/achievement/achievement.service';
import { IBehaviour } from 'app/shared/model/behaviour.model';
import { BehaviourService } from 'app/entities/behaviour/behaviour.service';

@Component({
  selector: 'jhi-achievement-behaviour-update',
  templateUrl: './achievement-behaviour-update.component.html'
})
export class AchievementBehaviourUpdateComponent implements OnInit {
  isSaving: boolean;

  achievements: IAchievement[];

  behaviours: IBehaviour[];

  editForm = this.fb.group({
    id: [],
    times: [null, [Validators.required, Validators.min(1)]],
    achievementId: [],
    behaviourId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected achievementBehaviourService: AchievementBehaviourService,
    protected achievementService: AchievementService,
    protected behaviourService: BehaviourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ achievementBehaviour }) => {
      this.updateForm(achievementBehaviour);
    });
    this.achievementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAchievement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAchievement[]>) => response.body)
      )
      .subscribe((res: IAchievement[]) => (this.achievements = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.behaviourService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBehaviour[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBehaviour[]>) => response.body)
      )
      .subscribe((res: IBehaviour[]) => (this.behaviours = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(achievementBehaviour: IAchievementBehaviour) {
    this.editForm.patchValue({
      id: achievementBehaviour.id,
      times: achievementBehaviour.times,
      achievementId: achievementBehaviour.achievementId,
      behaviourId: achievementBehaviour.behaviourId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const achievementBehaviour = this.createFromForm();
    if (achievementBehaviour.id !== undefined) {
      this.subscribeToSaveResponse(this.achievementBehaviourService.update(achievementBehaviour));
    } else {
      this.subscribeToSaveResponse(this.achievementBehaviourService.create(achievementBehaviour));
    }
  }

  private createFromForm(): IAchievementBehaviour {
    return {
      ...new AchievementBehaviour(),
      id: this.editForm.get(['id']).value,
      times: this.editForm.get(['times']).value,
      achievementId: this.editForm.get(['achievementId']).value,
      behaviourId: this.editForm.get(['behaviourId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAchievementBehaviour>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAchievementById(index: number, item: IAchievement) {
    return item.id;
  }

  trackBehaviourById(index: number, item: IBehaviour) {
    return item.id;
  }
}
