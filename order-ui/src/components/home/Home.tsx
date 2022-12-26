import React, { useEffect, useState } from 'react'
import { Container, Dimmer, Grid, Icon, Image, Loader, Segment, Statistic } from 'semantic-ui-react'
import { orderApi } from '../../api/OrderApi'
import { handleLogError } from '../../util/Helpers'

export const Home = () => {
  const [numberOfUsers, setNumberOfUsers] = useState<number>(0);
  const [numberOfOrders, setNumberOfOrders] = useState<number>(0);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  useEffect(() => {
    setIsLoading(true)
    const fetchData = async () => {
      const usersResponse = await orderApi.numberOfUsers()
      setNumberOfUsers(usersResponse.data)

      const ordersResponse = await orderApi.numberOfOrders()
      setNumberOfOrders(ordersResponse.data)
    }

    fetchData()
      .catch(error => handleLogError(error))
      .finally(() => setIsLoading(false));
  }, [])

  const loadingFragment = () => (
    <Segment basic style={{ marginTop: window.innerHeight / 2 }}>
      <Dimmer active inverted>
        <Loader inverted size='huge'>Loading</Loader>
      </Dimmer>
    </Segment>
  )

  const main = () => (
    <Container text>
      <Grid stackable columns={2}>
        <Grid.Row>
          <Grid.Column textAlign='center'>
            <Segment color='violet'>
              <Statistic>
                <Statistic.Value><Icon name='user' color='grey' />{numberOfUsers}</Statistic.Value>
                <Statistic.Label>Users</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
          <Grid.Column textAlign='center'>
            <Segment color='violet'>
              <Statistic>
                <Statistic.Value><Icon name='laptop' color='grey' />{numberOfOrders}</Statistic.Value>
                <Statistic.Label>Orders</Statistic.Label>
              </Statistic>
            </Segment>
          </Grid.Column>
        </Grid.Row>
      </Grid>

      <Image src='https://react.semantic-ui.com/images/wireframe/media-paragraph.png' style={{ marginTop: '2em' }} />
      <Image src='https://react.semantic-ui.com/images/wireframe/paragraph.png' style={{ marginTop: '2em' }} />
    </Container>
  )

  return <>
    {isLoading ? loadingFragment() : main()}
  </>
};

export default Home