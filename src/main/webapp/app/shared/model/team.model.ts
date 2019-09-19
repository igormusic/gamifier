import { IEmployee } from 'app/shared/model/employee.model';

export interface ITeam {
  id?: number;
  name?: string;
  employees?: IEmployee[];
}

export class Team implements ITeam {
  constructor(public id?: number, public name?: string, public employees?: IEmployee[]) {}
}
