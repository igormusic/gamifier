import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILevel, Level } from 'app/shared/model/level.model';
import { LevelService } from './level.service';

@Component({
  selector: 'jhi-level-update',
  templateUrl: './level-update.component.html'
})
export class LevelUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3)]],
    points: [null, [Validators.required, Validators.min(1)]]
  });

  constructor(protected levelService: LevelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ level }) => {
      this.updateForm(level);
    });
  }

  updateForm(level: ILevel) {
    this.editForm.patchValue({
      id: level.id,
      name: level.name,
      points: level.points
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const level = this.createFromForm();
    if (level.id !== undefined) {
      this.subscribeToSaveResponse(this.levelService.update(level));
    } else {
      this.subscribeToSaveResponse(this.levelService.create(level));
    }
  }

  private createFromForm(): ILevel {
    return {
      ...new Level(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      points: this.editForm.get(['points']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILevel>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
