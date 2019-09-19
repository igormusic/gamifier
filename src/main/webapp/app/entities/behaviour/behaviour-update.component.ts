import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBehaviour, Behaviour } from 'app/shared/model/behaviour.model';
import { BehaviourService } from './behaviour.service';

@Component({
  selector: 'jhi-behaviour-update',
  templateUrl: './behaviour-update.component.html'
})
export class BehaviourUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(3)]],
    points: [null, [Validators.required, Validators.min(1)]]
  });

  constructor(protected behaviourService: BehaviourService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ behaviour }) => {
      this.updateForm(behaviour);
    });
  }

  updateForm(behaviour: IBehaviour) {
    this.editForm.patchValue({
      id: behaviour.id,
      name: behaviour.name,
      points: behaviour.points
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const behaviour = this.createFromForm();
    if (behaviour.id !== undefined) {
      this.subscribeToSaveResponse(this.behaviourService.update(behaviour));
    } else {
      this.subscribeToSaveResponse(this.behaviourService.create(behaviour));
    }
  }

  private createFromForm(): IBehaviour {
    return {
      ...new Behaviour(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      points: this.editForm.get(['points']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBehaviour>>) {
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
