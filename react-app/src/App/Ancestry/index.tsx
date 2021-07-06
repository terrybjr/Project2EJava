import React from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

type Props = {};

class Ancestry extends React.Component {
  chosenCd: string;

  constructor(props: Props) {
    super(props);
    this.chosenCd = 'Dwf';
  }

  set_chosenCd(value: string) {
    this.chosenCd = value;
  }

  get_chosenCd() {
    return this.chosenCd;
  }

  onSelect() {
    this.set_chosenCd('Dwarf');
  }

  render() {
    return (
      <List>
        {['Dwarf', 'Elf', 'Gnome', 'Halfling', 'Human', 'Orc'].map((text, index) => (
          <ListItem button key={text}>
            <ListItemText primary={text} />
          </ListItem>
          // eslint-disable-next-line @typescript-eslint/comma-dangle
        ))}
      </List>
    );
  }
}

export default Ancestry;
