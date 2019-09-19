import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAchievementProgress, AchievementProgress } from 'app/shared/model/achievement-progress.model';
import { AchievementProgressService } from './achievement-progress.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';

@Component({
  selector: 'jhi-achievement-progress-update',
  templateUrl: './achievement-progress-update.component.html'
})
export class AchievementProgressUpdateComponent implements OnInit {
  isSaving: boolean;

  activities: IActivity[];

  editForm = this.fb.group({
    id: [],
    status: [],
    activities: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected achievementProgressService: AchievementProgressService,
    protected activityService: ActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ achievementProgress }) => {
      this.updateForm(achievementProgress);
    });
    this.activityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IActivity[]>) => mayBeOk.ok),
        map((response: HttpResponse<IActivity[]>) => response.body)
      )
      .subscribe((res: IActivity[]) => (this.activities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(achievementProgress: IAchievementProgress) {
    this.editForm.patchValue({
      id: achievementProgress.id,
      status: achievementProgress.status,
      activities: achievementProgress.activities
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const achievementProgress = this.createFromForm();
    if (achievementProgress.id !== undefined) {
      this.subscribeToSaveResponse(this.achievementProgressService.update(achievementProgress));
    } else {
      this.subscribeToSaveResponse(this.achievementProgressService.create(achievementProgress));
    }
  }

  private createFromForm(): IAchievementProgress {
    return {
      ...new AchievementProgress(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      activities: this.editForm.get(['activities']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAchievementProgress>>) {
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

  trackActivityById(index: number, item: IActivity) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
