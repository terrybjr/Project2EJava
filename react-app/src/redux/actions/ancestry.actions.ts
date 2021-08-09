import { Ancestry } from '../../models/Ancestry.model';

export const UPDATE_ANCESTRY = 'UPDATE_ANCESTRY';

export const updateAncestry = (ancestry: Ancestry) => ({
  type: UPDATE_ANCESTRY,
  payload: ancestry,
});
