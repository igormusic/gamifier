export interface ILevel {
  id?: number;
  name?: string;
  points?: number;
}

export class Level implements ILevel {
  constructor(public id?: number, public name?: string, public points?: number) {}
}
