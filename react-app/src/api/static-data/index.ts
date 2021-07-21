import axios from 'axios';
import Ancestry from '../../App/Ancestry';
import { dungenUrl } from '../index';

export const fetchAncestryData = () => {
  const url = `${dungenUrl}/data/getStaticData/ancestry`;
  return axios.get<Ancestry[]>(url).catch((error) => error);
};
