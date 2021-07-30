import React from 'react';
// import { connect } from 'react-redux';
import { Card } from '@material-ui/core';
import Grid from '@material-ui/core/Grid';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { Button } from '@material-ui/core';
import { Character as CharacterInterface } from '../../models/Character.model';
/* import { fetchAncestryData } from '../../api/static-data';
import { Ancestry as AncestryInterface } from '../../models/Ancestry.model';
import { updateAncestry } from '../../redux/actions/ancestry.actions'; */
import './index.css';

type State = {
   characters: CharacterInterface[];
   index: number;
};

class PlayerChars extends React.Component {
   state: State = {
      characters: [{name: 'Finrod', class: 'Wizard', level: 5}, {name: 'Melda', class: 'Medic', level: 7}],
      index: 100,
   };

   render () {
      return (
         // <div>Player_Chars</div>
         <Grid container spacing={2}>
        <Grid item xs={8}>
          <Card>
            <List>
              <ListItem>
                <ListItemText primary="Your Characters" />
              </ListItem>
              {this.state.characters.map((character, index) => (
                <ListItem
/*                   selected={this.is_selected_item(index)}
                  onClick={() => this.onSelect(index)}
                  onMouseEnter={() => this.setState({ mouseOn: index })}
                  onMouseLeave={() => this.setState({ mouseOn: 100 })} */
                  button
                  key={character.name}
                >
                  <ListItemText
                    primary={character.name}
                  />
                  <ListItemText
                    secondary={character.class}
                    />
                  Level: {character.level}
                </ListItem>
              ))}
            </List>
          </Card>
        </Grid>
        <Grid item xs={8}>
          <Card> Click on Character to view or update
                <Button className="button" variant="contained">Create New Character</Button><p></p>
          </Card>
        </Grid>
      </Grid>

      );
   }
}

export default PlayerChars;