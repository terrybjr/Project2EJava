import axios from 'axios';
import { Ancestry } from '../../models/Ancestry.model';
import { dungenUrl } from '../index';

export const fetchAncestryData = () => {
  const url = `${dungenUrl}/data/getStaticData/ancestry`;
  return axios.get<Ancestry[]>(url);
};
