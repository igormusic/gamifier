import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { IBehaviour } from 'app/shared/model/behaviour.model';
import { BehaviourService } from 'app/entities/behaviour/behaviour.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';
import { AchievementProgressService } from 'app/entities/achievement-progress/achievement-progress.service';

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  behaviours: IBehaviour[];

  employees: IEmployee[];

  achievementprogresses: IAchievementProgress[];

  editForm = this.fb.group({
    id: [],
    dateTime: [],
    behaviourId: [],
    employeeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityService: ActivityService,
    protected behaviourService: BehaviourService,
    protected employeeService: EmployeeService,
    protected achievementProgressService: AchievementProgressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);
    });
    this.behaviourService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBehaviour[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBehaviour[]>) => response.body)
      )
      .subscribe((res: IBehaviour[]) => (this.behaviours = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.achievementProgressService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAchievementProgress[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAchievementProgress[]>) => response.body)
      )
      .subscribe(
        (res: IAchievementProgress[]) => (this.achievementprogresses = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(activity: IActivity) {
    this.editForm.patchValue({
      id: activity.id,
      dateTime: activity.dateTime != null ? activity.dateTime.format(DATE_TIME_FORMAT) : null,
      behaviourId: activity.behaviourId,
      employeeId: activity.employeeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id']).value,
      dateTime: this.editForm.get(['dateTime']).value != null ? moment(this.editForm.get(['dateTime']).value, DATE_TIME_FORMAT) : undefined,
      behaviourId: this.editForm.get(['behaviourId']).value,
      employeeId: this.editForm.get(['employeeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>) {
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

  trackBehaviourById(index: number, item: IBehaviour) {
    return item.id;
  }

  trackEmployeeById(index: number, item: IEmployee) {
    return item.id;
  }

  trackAchievementProgressById(index: number, item: IAchievementProgress) {
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
