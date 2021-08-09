import { UPDATE_ANCESTRY } from '../actions/ancestry.actions';
import { Ancestry } from '../../models/Ancestry.model';

interface Action {
  type: string;
  payload: Ancestry;
}

const initialState = {};

const ancestryReducer = (state: Ancestry | {} = initialState, action: Action) => {
  switch (action.type) {
    case UPDATE_ANCESTRY: {
      return action.payload;
    }
    default:
      return state;
  }
};

export default ancestryReducer;
