export interface IAchievementBehaviour {
  id?: number;
  times?: number;
  achievementName?: string;
  achievementId?: number;
  behaviourName?: string;
  behaviourId?: number;
}

export class AchievementBehaviour implements IAchievementBehaviour {
  constructor(
    public id?: number,
    public times?: number,
    public achievementName?: string,
    public achievementId?: number,
    public behaviourName?: string,
    public behaviourId?: number
  ) {}
}
